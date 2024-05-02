package community.server.domain.user.mapper;

import community.server.domain.user.banker.Banker;
import java.util.Arrays;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-29T17:02:16+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 21.0.1 (Oracle Corporation)"
)
public class BankerMapperImpl implements BankerMapper {

    @Override
    public Banker userDtoToUserModel(Banker banker) {
        if ( banker == null ) {
            return null;
        }

        byte[] banker_uuid = null;
        String banker_name = null;
        String banker_password = null;
        boolean authentication = false;

        byte[] banker_uuid1 = banker.banker_uuid();
        if ( banker_uuid1 != null ) {
            banker_uuid = Arrays.copyOf( banker_uuid1, banker_uuid1.length );
        }
        banker_name = banker.banker_name();
        banker_password = banker.banker_password();
        authentication = banker.authentication();

        Banker banker1 = new Banker( banker_uuid, banker_name, banker_password, authentication );

        return banker1;
    }
}
