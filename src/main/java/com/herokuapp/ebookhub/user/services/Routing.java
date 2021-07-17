package com.herokuapp.ebookhub.user.services;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;

// import org.springframework.web.bind.annotation.RequestMapping;

// import java.util.Map;

// import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
// import org.springframework.boot.autoconfigure.domain.EntityScan;
// import org.springframework.context.annotation.ComponentScan;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// import org.springframework.transaction.annotation.EnableTransactionManagement;

// import org.springframework.web.bind.annotation.RestController;

// import org.springframework.context.annotation.ComponentScan;

// @EnableAutoConfiguration
// @ComponentScan(basePackages={"com.herokuapp.ebookhub"})
// @EnableJpaRepositories(basePackages="com.herokuapp.ebookhub.user.repositories")
// @EnableTransactionManagement
// @EntityScan(basePackages="com.herokuapp.ebookhub.user.entities")
// @ComponentScan(basePackageClasses = com.herokuapp.ebookhub)
// @RestController
public class Routing {

    // @Bean
    // public CommandLineRunner run(UserRepository userRepository) throws Exception {
    //     return (String[] args) -> {
    //         userRepository.findAll().forEach(user -> System.out.println(user.getEmail() + " " + user.getRole()));
    //     };
    // }

    // @RequestMapping("/")
	// String index() {
	// 	return "index";
	// }

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
      // try {


    //     userRepository.findAll().forEach(user -> 
		// model.put(user.getEmail(), user.getRole()));
        // for (int i = 0; i < output.size(); i++) {
        //   model.put(output.get(i).getEmail(), output.get(i).getRole());
        // }

        // return "db";
    // } catch (Exception e) {
    //   model.put("message", e.getMessage());
    //   return "error";
    // }
   }*/

   //   @Autowired


/*
  private DataSource dataSource;*/

  // public static void main(String[] args) throws Exception {

}