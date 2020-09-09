FROM openjdk:8-jdk-alpine

# Prepare working directories
WORKDIR /
RUN mkdir /repo
RUN mkdir /app

# Prepare repository data
COPY ./ /repo

# Build application
WORKDIR /repo
RUN chmod +x gradlew
RUN ./gradlew build

# Install application
RUN cp /repo/build/libs/project-raisin-cognito-simple-web-app*.jar /app/project-raisin-cognito-simple-web-app.jar

# Clean building data
# (--recursive, --force)
WORKDIR /
RUN rm -R -f /repo

# Run application
WORKDIR /app
EXPOSE 12345
CMD [ "java", "-jar", "project-raisin-cognito-simple-web-app.jar" ]
