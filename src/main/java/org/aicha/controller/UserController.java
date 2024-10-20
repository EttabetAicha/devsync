package org.aicha.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.aicha.model.Token;
import org.aicha.model.User;
import org.aicha.model.enums.UserRole;
import org.aicha.service.TokenService;
import org.aicha.service.UserService;
import org.aicha.util.StringUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    public UserController() {
        userService = new UserService();
        tokenService = new TokenService();
    }

    public void index(HttpServletRequest request, HttpServletResponse response) {
        // Check session and role
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            try {
                User user = (User) session.getAttribute("user");
                if (user.getRole() == UserRole.manager) {
                    List<User> users = userService.getAllUsers();
                    request.setAttribute("users", users);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("users/index.jsp");
                    try {
                        requestDispatcher.forward(request, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    response.sendRedirect(request.getContextPath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.sendRedirect(request.getContextPath() + "/users?action=login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void create(HttpServletRequest request, HttpServletResponse response) {
        // session management
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            try {
                User user = (User) session.getAttribute("user");
                if (user.getRole() == UserRole.manager) {
                    response.sendRedirect(request.getContextPath() + "/users");
                } else {
                    response.sendRedirect(request.getContextPath() + "/tasks");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("auth/register.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        UserRole role = UserRole.valueOf(request.getParameter("role"));

        // Create a new User object
        User newUser = new User(username, email, password, firstName, lastName, role);

        Optional<User> registeredUser = userService.createUser(newUser);
        registeredUser.ifPresentOrElse(
                user -> {
                    try {
                        // Token Initialization
                        Token token = tokenService.createToken(new Token(user, 2, 1, LocalDateTime.now()));
                        user.setToken(token);
                        request.getSession().setAttribute("user", user);
                        if (user.getRole() == UserRole.manager) {
                            response.sendRedirect(request.getContextPath() + "/users");
                        } else {
                            response.sendRedirect(request.getContextPath() + "/tasks");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                },
                () -> {
                    try {
                        PrintWriter out = response.getWriter();
                        out.println("<html><body><h1 style=\"color:red;\">Registration failed</h1><");
                        out.println("<h2> registered user : " + registeredUser + "</h2></body></html>");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.parseLong(request.getParameter("id"));
        Optional<User> user = userService.getUserById(id);
        user.ifPresent(value -> {
            request.setAttribute("user", value);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("users/edit.jsp");
            try {
                requestDispatcher.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void update(HttpServletRequest request, HttpServletResponse response) {
        Long userId = Long.parseLong(request.getParameter("id"));
        Optional<User> user = userService.getUserById(userId);
        user.ifPresentOrElse(
                u -> {
                    u.setUsername(request.getParameter("username"));
                    u.setEmail(request.getParameter("email"));
                    u.setPassword(request.getParameter("password"));
                    u.setFirstName(request.getParameter("firstName"));
                    u.setLastName(request.getParameter("lastName"));
                    u.setRole(UserRole.valueOf(request.getParameter("role")));
                    Optional<User> updatedUser = userService.updateUser(u);
                    updatedUser.ifPresentOrElse(
                            user1 -> {
                                try {
                                    response.sendRedirect(request.getContextPath() + "/users");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            },
                            () -> {
                                try {
                                    PrintWriter out = response.getWriter();
                                    out.println("<html><body><h1 style=\"color:red;\">User update failed</h1></body></html>");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                },
                () -> {
                    try {
                        PrintWriter out = response.getWriter();
                        out.println("<html><body><h1 style=\"color:red;\">User not found</h1></body></html>");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) {
        Long userId = Long.parseLong(request.getParameter("id"));
        Optional<User> user = userService.getUserById(userId);
        user.ifPresentOrElse(
                u -> {
                    if (userService.deleteUser(u) != null) {
                        try {
                            response.sendRedirect(request.getContextPath() + "/users");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            PrintWriter out = response.getWriter();
                            out.println("<html><body><h1 style=\"color:red;\">User deletion failed</h1></body></html>");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                () -> {
                    try {
                        PrintWriter out = response.getWriter();
                        out.println("<html><body><h1 style=\"color:red;\">User not found</h1></body></html>");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void login(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Optional<User> user = userService.getUserByEmail(email);
        user.ifPresentOrElse(
                u -> {
                    if (u.getPassword().equals(StringUtil.hashPassword(password))) {
                        try {
                            // session management
                            request.getSession().setAttribute("user", u);
                            if (u.getRole() == UserRole.manager) {
                                response.sendRedirect(request.getContextPath() + "/users");
                            } else {
                                response.sendRedirect(request.getContextPath() + "/tasks");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            response.sendRedirect(request.getContextPath() + "/users?action=login&error=invalid-credentials");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                () -> {
                    try {
                        response.sendRedirect(request.getContextPath() + "/users?action=login&error=invalid-credentials");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void loginForm(HttpServletRequest request, HttpServletResponse response) {
        // Check session
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            try {
                User user = (User) session.getAttribute("user");
                if (user.getRole() == UserRole.manager) {
                    response.sendRedirect(request.getContextPath() + "/users");
                } else {
                    response.sendRedirect(request.getContextPath() + "/tasks");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        RequestDispatcher view = request.getRequestDispatcher("auth/login.jsp");
        try {
            view.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false); // Fetch session if it exists
        if (session != null) {
            session.invalidate(); // Invalidate the session
        }
        try {
            response.sendRedirect(request.getContextPath() + "/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
