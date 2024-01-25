up:
    sudo docker-compose -f docker-compose.yml up -d --build --force-recreate app
down:
    sudo docker-compose -f docker-compose.yml down app
update:
    make down
    sudo docker system prune -f -a --volumes
    git pull
    make up
