module.exports = {
    mode: "development",
    entry: "./src/main/js/index.tsx",
    output: {
        filename: "bundle.js",
        path: __dirname + "/target/classes/public"
    },
    devtool: "inline-source-map",
    resolve: {
        extensions: [".ts", ".tsx", ".js", "json", ".jsx", ".css", ".scss"]
    },
    module: {
        rules: [
            {
                test: /\.(ts|tsx)?$/,
                exclude: /node_modules/,
                loader: "ts-loader"
            },
            {
                test: /\.(js|jsx)$/,
                enforce: 'pre',
                loader: 'eslint-loader'
            },
            {
                test: /\.(js|jsx)$/,
                loader: 'babel-loader'
            },
            {
                test: /\.css$/,
                use: [
                    'style-loader',
                    'css-loader'
                ]
            },
            {
                test: /\.s[ac]ss$/i,
                use: [
                    // Creates `style` nodes from JS strings
                    'style-loader',
                    // Translates CSS into CommonJS
                    'css-loader',
                    // Compiles Sass to CSS
                    'sass-loader',
                ],
            },
            {
                test: /\.(jpe?g|png|gif|svg)$/i,
                loader: "file-loader?name=/public/icons/[name].[ext]"
            },
            {
                test: /\.(mov|mp4)$/,
                use: [
                    'file-loader'
                ]
            },
            {
                test: /.(ttf|otf|eot|svg|woff(2)?)(\?[a-z0-9]+)?$/,
                exclude: /images/,  /* dont want svg images from image folder to be included */
                use: [
                    {
                        loader: 'file-loader',
                        options: {
                            outputPath: 'fonts/',
                            name: '[name][hash].[ext]',
                        },
                    },
                ],
            }]
    }
};