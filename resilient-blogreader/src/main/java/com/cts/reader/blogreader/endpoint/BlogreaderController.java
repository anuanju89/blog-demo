package com.cts.reader.blogreader.endpoint;

import com.cts.reader.blogreader.service.BlogreaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.StreamSupport;

@Controller
public class BlogreaderController {

	private static final Logger log = LoggerFactory.getLogger(BlogreaderController.class);

	@Autowired
	private BlogreaderService readerService;

	@RequestMapping("/")
	public ResponseEntity<String> greeting() {
		return new ResponseEntity<String>(readerService.getInfo(), HttpStatus.OK);
	}

	@RequestMapping(value = "/blogs", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Iterable<Map>> index() {
		return new ResponseEntity<Iterable<Map>>(readerService.getBlogs(), HttpStatus.OK);
	}

	@GetMapping("/blog/{id}")
	@ResponseBody
	public ResponseEntity<Map> show(@PathVariable String id) {
		log.debug(" ... searching for {}", id);
		Map blog = readerService.getBlogById(id);
		if (blog == null || blog.isEmpty()) {
			return new ResponseEntity<Map>(blog, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Map>(blog, HttpStatus.OK);
		}
	}

}