FROM node:20.11.0

WORKDIR /usr/src/app

COPY . /usr/src/app
COPY package.json package-lock.json ./

RUN npm install -g @angular/cli

RUN npm install

RUN ng build

CMD ["npm", "run", "start"]
