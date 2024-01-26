up:
	sudo docker-compose -f docker-compose.yml up -d --build --force-recreate --no-deps farm-app
down:
	sudo docker-compose -f docker-compose.yml down farm-app
update:
	git pull
	make up
up-all:
	eval `ssh-agent -s`
	ssh-add ~/.ssh/github
	sudo docker-compose -f docker-compose.yml up -d --build
down-all:
	sudo docker-compose -f docker-compose.yml down
