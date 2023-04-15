package com.petkpetk.service.domain.shopping.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.petkpetk.service.domain.shopping.dto.item.ItemSearchDto;
import com.petkpetk.service.domain.shopping.dto.item.MainItemDto;
import com.petkpetk.service.domain.shopping.service.item.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    @GetMapping("/layout")
    public String test(){
        return "/layout/myPageLayout";
    }

    @GetMapping("/")
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){

        PageRequest pageRequest = PageRequest.of(page.orElse(0), 10);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageRequest);

        System.out.println("◆◆◆◆◆◆◆◆◆◆◆◆◆◆ pageRequest = " + pageRequest);
        System.out.println("◆◆◆◆◆◆◆◆◆◆◆◆◆◆ items = " + items);
        items.stream().forEach(System.out::println);

        System.out.println("itemSearchDto = " + itemSearchDto);


        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage",5);
        return "main";

    }


}
