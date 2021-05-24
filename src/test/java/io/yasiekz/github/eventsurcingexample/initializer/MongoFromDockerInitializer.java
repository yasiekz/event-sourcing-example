package io.yasiekz.github.eventsurcingexample.initializer;

import com.mongodb.connection.ClusterConnectionMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy;
import org.testcontainers.utility.DockerImageName;

public class MongoFromDockerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final int TIMEOUT_MS = 1500;
    private static final int CONNECTION_LIFETIME_MS = 60000;
    private static final MongoDBContainer mongo = new MongoDBContainer(DockerImageName.parse("mongo:4.4.6"));
    private static final Logger logger = LoggerFactory.getLogger(MongoFromDockerInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        logger.info("Initialize mongo from test containers");
        mongo.setWaitStrategy(new HostPortWaitStrategy());
        mongo.start();

        TestPropertyValues valuesSpringData = TestPropertyValues.of(
            "spring.data.mongodb.uri=" + mongo.getReplicaSetUrl(),
            "spring.data.mongodb.timeout=" + TIMEOUT_MS,
            "spring.data.mongodb.clusterMode=" + ClusterConnectionMode.SINGLE,
            "spring.data.mongodb.connectionLifeTimeMs=" + CONNECTION_LIFETIME_MS);
        valuesSpringData.applyTo(applicationContext);
    }
}
