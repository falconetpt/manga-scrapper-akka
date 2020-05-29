package com.rfgomes.manga4all.manga

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RestController}

@RestController
@RequestMapping(Array("/hello"))
class MangaController {
    @GetMapping
    def hello: ResponseEntity[Any] = {
      ResponseEntity.ok("hello")
    }
}
