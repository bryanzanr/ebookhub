/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.herokuapp.ebookhub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.herokuapp.ebookhub.user.entities.UserRepository;
import java.util.Map;

// import org.springframework.boot.CommandLineRunner;
// import com.herokuapp.ebookhub.user.entities.User;
// import java.util.List;

// import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
// import org.springframework.boot.autoconfigure.domain.EntityScan;
// import org.springframework.context.annotation.ComponentScan;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// import org.springframework.transaction.annotation.EnableTransactionManagement;

@Controller
// @EnableAutoConfiguration
// @ComponentScan(basePackages={"com.herokuapp.ebookhub"})
// @EnableJpaRepositories(basePackages="com.herokuapp.ebookhub.user.repositories")
// @EnableTransactionManagement
// @EntityScan(basePackages="com.herokuapp.ebookhub.user.entities")
@SpringBootApplication
public class Main {

//   @Autowired
  private UserRepository userRepository;

  @Autowired
  public Main(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


/*
  private DataSource dataSource;*/

  // public static void main(String[] args) throws Exception {
  public static void main (String[] args) {
    SpringApplication.run(Main.class, args);
  }

  @GetMapping("/")
  String index() {
    return "index";
  }

 /* @RequestMapping("/db")
  String db(Map<String, Object> model) {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
      ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

      ArrayList<String> output = new ArrayList<String>();
      while (rs.next()) {
        output.add("Read from DB: " + rs.getTimestamp("tick"));
      }

      model.put("records", output);
      return "db";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }*/

  @RequestMapping("/db")
	// @Bean
//   public CommandLineRunner run(UserRepository userRepository) throws Exception {
  String db(Map<String, Object> model) {
	// return (String[] args) -> {
	// 	userRepository.findAll().forEach(user -> System.out.println(user.getEmail() + " " + user.getRole()));
	// };
    //   try {
        userRepository.findAll().forEach(user -> 
		model.put(user.getEmail(), user.getRole()));
        // for (int i = 0; i < output.size(); i++) {
        //   model.put(output.get(i).getEmail(), output.get(i).getRole());
        // }

        return "db";
    // } catch (Exception e) {
    //   model.put("message", e.getMessage());
    //   return "error";
    // }
  }

}