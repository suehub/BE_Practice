package community.server.domain.user.mapper;

import community.server.domain.user.banker.Banker;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-27T20:25:57+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 21.0.1 (Oracle Corporation)"
)
public class BankerMapperImpl implements BankerMapper {

    @Override
    public Banker userDtoToUserModel(Banker banker) {
        if ( banker == null ) {
            return null;
        }

        String name = null;
        String password = null;
        String authentication = null;

        name = banker.name();
        password = banker.password();
        authentication = banker.authentication();

        Banker banker1 = new Banker( name, password, authentication );

        return banker1;
    }
}
