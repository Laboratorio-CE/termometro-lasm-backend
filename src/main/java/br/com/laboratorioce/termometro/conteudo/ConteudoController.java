package br.com.laboratorioce.termometro.conteudo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ConteudoController {

	@GetMapping("/hello")
	public String hello() {
		return "Olá mundo!";
	}
}
