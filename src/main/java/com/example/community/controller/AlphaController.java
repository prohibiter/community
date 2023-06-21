package com.example.community.controller;

import com.example.community.entity.BeanTest;
import com.example.community.service.AlphaService;
import com.example.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody // 直接返回字符串，不通过前端控制器
    public String sayHello() {
        return "hello";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        final Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String name = headerNames.nextElement();
            final String value = request.getHeader(name);
            System.out.println(name + ":" + value);
        }

        // 返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try (final PrintWriter writer = response.getWriter()) {
            writer.write("<h1>张三</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // GET请求

    // /student?current=1&limit=20
    @RequestMapping(path = "/student", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(@RequestParam(name = "current", required = false, defaultValue = "1") int current, @RequestParam(name = "limit", required = false, defaultValue = "20") int limit) {
        System.out.println(current + "/" + limit);
        return "some student";
    }

    // /student/123
    @RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {
        System.out.println(id);
        return "a student";
    }

    @RequestMapping(value = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, String age) {
        System.out.println(name + "/" + age);
        return "success";
    }

    // 相应html数据
    @RequestMapping(value = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "张三");
        mav.addObject("age", 30);
        mav.setViewName("/demo/view");
        return mav;
    }

    @RequestMapping(value = "/school", method = RequestMethod.GET)
    public String getSchool(Model model) {
        model.addAttribute("name", "北工大");
        model.addAttribute("age", 80);
        return "/demo/view";
    }

    @Autowired
    private BeanTest emp;

    // 响应JSON数据（异步请求）
    @RequestMapping(value = "/emp", method = RequestMethod.GET)
    @ResponseBody
    public BeanTest getEmp() {
        // Map<String, Object> emp = new HashMap<>();
        // emp.put("name", "张三");
        // emp.put("age", 23);
        // emp.put("salary", 8000.00);
        return emp;
    }

    //响应多个员工
    @RequestMapping(value = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<BeanTest> getEmps() {
        BeanTest emp1 = new BeanTest();
        emp1.setName("张三");
        emp1.setAge(23);
        emp1.setSalary(100);
        BeanTest emp2 = new BeanTest();
        emp2.setName("李四");
        emp2.setAge(2);
        emp2.setSalary(10);
        List<BeanTest> emps = new ArrayList<BeanTest>() {{
            add(emp1);
            add(emp2);
        }};
        return emps;
    }

    //cookie
    @RequestMapping(value = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response) {
        // 创建cookie
        Cookie cookie = new Cookie("code", CommunityUtil.generationUUID());
        // 设置cookie生效范围
        cookie.setPath("/community/alpha");
        // 设置cookie的生存时间
        cookie.setMaxAge(60 * 10);
        // 发送cookie
        response.addCookie(cookie);
        return "set cookie";
    }

    @RequestMapping(value = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code) {
        System.out.println(code);
        return "get cookie";
    }

    @RequestMapping(value = "/session/set", method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session) {
        session.setAttribute("id", 1);
        session.setAttribute("name", "test");
        return "set session";
    }

    @RequestMapping(value = "/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session) {
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }

    @RequestMapping(value = "/ajax",method = RequestMethod.POST)
    @ResponseBody
    public String testAjax(String name, int age){
        System.out.println(name);
        System.out.println(age);
        return CommunityUtil.getJSONString(0,"操作成功！");
    }
}
