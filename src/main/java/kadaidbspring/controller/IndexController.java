package kadaidbspring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kadaidbspring.controller.form.PersonForm;


@RestController
public class IndexController {
	//Autowiredは初期化、簡略化してくれる
	@Autowired
	private JdbcTemplate jdbcTemplate;
		@GetMapping("/api/selectAll")
		//↓DBのテーブルのデータを全件取得して結果を返却する処理
        public List<Map<String, Object>> getAll(Model model) {
	    	List<Map<String, Object>> personList = new ArrayList<Map<String, Object>>();
	        personList = jdbcTemplate.queryForList("SELECT * FROM person");
	        return personList;
     }
    @GetMapping("/api/select/{id}")
    //一件取得の処理。{id}に入った数字が@PathVariable("id")のidと一致したら、String idのidに入る
    public List<Map<String, Object>> getById(@PathVariable("id") String id) {
    	List<Map<String, Object>> personList = new ArrayList<Map<String, Object>>();
        personList = jdbcTemplate.queryForList("SELECT * FROM person where id="+id);
        return personList;
 }
    @PutMapping("/api/update")
    @ResponseBody //jsonの形式に対応する記述
    public void update(@RequestBody @Validated PersonForm form) {
        jdbcTemplate.update("UPDATE person SET name='"+form.getName()+"', age="+form.getAge() +" where id="+form.getId());
 }
    @DeleteMapping("/api/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)//正常に処理が走ったら204を返す
    public void delete(@PathVariable("id") String id) {
        jdbcTemplate.update("DELETE FROM person where id ="+id);
 }
    @PostMapping("/api/insert")
    @ResponseStatus(HttpStatus.CREATED)//正常に処理が走ったら201を返す
    public void insert(@RequestBody @Validated PersonForm form) {
        jdbcTemplate.update("INSERT INTO person (name, age) VALUES ('"+form.getName()+"', "+form.getAge()+")");
 }
}
