up:
	sudo docker-compose -f docker-compose.yml up -d --build --force-recreate --no-deps farm-app
down:
	sudo docker-compose -f docker-compose.yml down farm-app
update:
	git pull
	make up
