package com.powerhouse.assignment.controller;

import com.powerhouse.assignment.exception.ValidationException;
import com.powerhouse.assignment.service.FileProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
public class FileUploadController {


    @Autowired
    private FileProcessingService fileProcessingService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
        model.addAttribute("dataPresent", false);
        if(fileProcessingService.isStaticFilePresent()){
            model.addAttribute("dataPresent", true);
            if(!model.containsAttribute("message")) {
                model.addAttribute("message",
                        "Data already been loaded by user or through static files present in data folder. Please" +
                                " go ahead to do other operations !");
            }
        }
        return "uploadForm";
    }

    @GetMapping("/refresh")
    public String refresh(Model model) throws IOException {
        model.addAttribute("dataPresent", false);
        fileProcessingService.cleanCache();
        return "uploadForm";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("fractionFile") MultipartFile fractionFile,
                                   @RequestParam("meterReadingFile") MultipartFile meterReadingFile,
                                   RedirectAttributes redirectAttributes) {

        try{
            fileProcessingService.processFile(destinationFile(fractionFile), destinationFile(meterReadingFile));
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded the data file !");
        }catch(IOException e){
            redirectAttributes.addFlashAttribute("message",
                    "Error in parsing file");
        }catch(ValidationException e){
            redirectAttributes.addFlashAttribute("message",
                    e.getMessage());
        }

        return "redirect:/";
    }



    private File destinationFile(MultipartFile file) throws IOException {

        if (!file.isEmpty()) {

            String realPathtoUploads = request.getServletContext().getRealPath("/upload");
            if (!new File(realPathtoUploads).exists()) {
                new File(realPathtoUploads).mkdir();
            }
            String orgName = file.getOriginalFilename();
            String filePath = realPathtoUploads + orgName;
            File dest = new File(filePath);
            file.transferTo(dest);
            return dest;
        }

        return null;
    }
}
