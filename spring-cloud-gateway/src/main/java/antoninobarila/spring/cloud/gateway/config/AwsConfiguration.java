package antoninobarila.spring.cloud.gateway.config;

/*@Component
@Data
@ConfigurationProperties(prefix = "spring.aws.secretsmanager")*/
public class AwsConfiguration {

    
    private String secretName;
    private String secretEndpoint;
    private String secretRegion;
    private String secretRoleArn;
    private String secretRoleSession;
    private String stsEndpoint;
    
}