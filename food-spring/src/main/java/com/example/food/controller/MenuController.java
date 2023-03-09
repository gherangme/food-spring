package com.example.food.controller;

import com.example.food.payload.ResponseData;
import com.example.food.service.imp.FileStorageServiceImp;
import com.example.food.service.imp.MenuServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/menu")
public class MenuController {


    @Autowired
    MenuServiceImp menuServiceImp;

    @Autowired
    FileStorageServiceImp fileStorageServiceImp;

    @PostMapping("")
    public ResponseEntity<?> addMenu(@RequestParam MultipartFile file,
                                     @RequestParam String name,
                                     @RequestParam String description,
                                     @RequestParam double price,
                                     @RequestParam String instruction,
                                     @RequestParam int cate_res_id) {
        System.out.println("kiem tra " + file.getOriginalFilename());
        boolean isSuccess = menuServiceImp.insertFood(file,name,description,price,instruction,cate_res_id);
        ResponseData responseData = new ResponseData();
        responseData.setData(isSuccess);

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("")
    private ResponseEntity<?> getAllMenu() {
        ResponseData responseData = new ResponseData();
        responseData.setData(menuServiceImp.getAllFood());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        Resource resource = fileStorageServiceImp.load(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);

    }

}
