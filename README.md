# DinoISuki

## Prerequisites for starting application locally

Run command:
 ```
mvn clean install -DskipTests
```

This command executes:
 * npm install (for installing packages from package.json and creating node_modules)
 * npm run-script build (for starting webpack and creating bundle.js file in target)
 
## Frontend development
Run command:
```
npm start
```

This command executes:
* webpack --debug --progress -w (for including changes in bundle.js automatically)

