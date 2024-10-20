package org.aicha.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.aicha.model.Tag;
import org.aicha.model.User;
import org.aicha.model.enums.UserRole;
import org.aicha.service.TagService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TagController {
    private final TagService tagService;

    public TagController() {
        tagService = new TagService();
    }

    public void index(HttpServletRequest request, HttpServletResponse response) {
        // Check session and role
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            try {
                User user = (User) session.getAttribute("user");
                if (user.getRole() == UserRole.manager) {
                    List<Tag> tags = tagService.getAllTags();
                    request.setAttribute("tags", tags);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("tags/index.jsp");
                    try{
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
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("tags/create.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        Tag tag = new Tag(name);
        tagService.createTag(tag);
        try {
            response.sendRedirect("tags");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.parseLong(request.getParameter("id"));
        Optional<Tag> tag = tagService.getTagById(id);
        tag.ifPresent(value -> {
            request.setAttribute("tag", value);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("tags/edit.jsp");
            try {
                requestDispatcher.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void update(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.parseLong(request.getParameter("id"));
        Optional<Tag> tag = tagService.getTagById(id);
        tag.ifPresent(value -> {
            value.setName(request.getParameter("name"));
            tagService.updateTag(value);
            try {
                response.sendRedirect("tags");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.parseLong(request.getParameter("id"));
        Optional<Tag> tag = tagService.getTagById(id);
        tag.ifPresent(value -> {
            tagService.deleteTag(value);
            try {
                response.sendRedirect("tags");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
