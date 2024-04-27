package community.server.domain.user.mapper;

import community.server.domain.user.customer.Customer;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-27T20:25:57+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 21.0.1 (Oracle Corporation)"
)
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer userDtoToUserModel(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        String name = null;
        String password = null;
        String role = null;

        name = customer.name();
        password = customer.password();
        role = customer.role();

        Customer customer1 = new Customer( name, password, role );

        return customer1;
    }
}
