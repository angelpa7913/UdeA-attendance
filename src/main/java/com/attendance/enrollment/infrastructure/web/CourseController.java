package com.attendance.enrollment.infrastructure.web;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.attendance.enrollment.api.ImportEnrollmentsUseCase;
import com.attendance.enrollment.api.ListInstructorCoursesUseCase;
import com.attendance.identity.api.CurrentUser;
import com.attendance.identity.api.GetCurrentUserUseCase;

/**
 * <h2>Propósito</h2>
 * Controlador delgado: lista los cursos del instructor e importa inscritos.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: valida entrada, llama un caso de uso y responde. Nada de repositorios.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code GET /instructor/courses} → vista {@code instructor/courses}.</li>
 *   <li>{@code POST /instructor/courses/import} (multipart {@code file}) → redirige con el resumen.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Controller}. Constructor con {@code GetCurrentUserUseCase}, {@code ListInstructorCoursesUseCase}, {@code ImportEnrollmentsUseCase}.</li>
 *   <li>Obtener el usuario actual; si está vacío, redirigir al login.</li>
 *   <li>GET: poner {@code courses} e {@code institutionName} en el {@code Model}.</li>
 *   <li>POST: llamar a {@code importEnrollments("csv", file.getInputStream(), email)}; mostrar el resumen con flash attributes.</li>
 *   <li>// SECURITY: el correo del instructor sale del usuario autenticado, nunca de un parámetro.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code identity.api.GetCurrentUserUseCase}</li>
 *   <li>api de enrollment</li>
 * </ul>
 */
@Controller
public class CourseController {

    private static final String COURSES_PATH = "/instructor/courses";
    private static final String COURSES_VIEW = "instructor/courses";
    private static final String REDIRECT_TO_COURSES = "redirect:" + COURSES_PATH;
    private static final String REDIRECT_TO_LOGIN = "redirect:/oauth2/authorization/oidc";
    private static final String CSV_SOURCE = "csv";

    private final GetCurrentUserUseCase getCurrentUser;
    private final ListInstructorCoursesUseCase listInstructorCourses;
    private final ImportEnrollmentsUseCase importEnrollments;

    public CourseController(GetCurrentUserUseCase getCurrentUser,
            ListInstructorCoursesUseCase listInstructorCourses, ImportEnrollmentsUseCase importEnrollments) {
        this.getCurrentUser = getCurrentUser;
        this.listInstructorCourses = listInstructorCourses;
        this.importEnrollments = importEnrollments;
    }

    @GetMapping(COURSES_PATH)
    public String courses(Model model) {
        return getCurrentUser.currentUser().map(user -> {
            model.addAttribute("courses", listInstructorCourses.coursesOf(requireInstructor(user).email()));
            return COURSES_VIEW;
        }).orElse(REDIRECT_TO_LOGIN);
    }

    @PostMapping(COURSES_PATH + "/import")
    public String importFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes)
            throws IOException {
        // SECURITY: el correo del instructor sale del usuario autenticado, nunca de un parámetro.
        final CurrentUser user = getCurrentUser.currentUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        attributes.addFlashAttribute("importSummary", importEnrollments.importEnrollments(CSV_SOURCE,
                file.getInputStream(), requireInstructor(user).email()));
        return REDIRECT_TO_COURSES;
    }

    // SECURITY: importar crea cursos y daría el rol de instructor a quien aún no lo tiene.
    private static CurrentUser requireInstructor(CurrentUser user) {
        if (!user.isInstructor()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return user;
    }
}
