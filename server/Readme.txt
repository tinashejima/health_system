------------------steps to run the project-------------------------

sdk use java 17.0.15-zulu

docker rm -v -f $(docker ps -qa)

cd /home/tynash/My-Projects/health_system/server && docker-compose down

cd /home/tynash/My-Projects/health_system/server && docker-compose up -d

docker rm -v -f $(docker ps -qa)

docker compose logs health_mysql


cd /home/tynash/My-Projects/health_system/server && mvn clean install;

--clean install skipping tests
 cd /home/tynash/My-Projects/health_system/server && mvn clean install -DskipTests


cd /home/tynash/My-Projects/health_system/server && mvn clean install;
cd /home/tynash/My-Projects/health_system/server && mvn spring-boot:run


docker volume rm mysql_data


----------------------run database---------

--if running outside the project first run

docker start health-mysql

docker exec -it health-mysql mysql -u tynash -p



GRANT ALL PRIVILEGES ON *.* TO 'tynash'@'%' IDENTIFIED BY 'password';

