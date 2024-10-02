const TerserPlugin = require("terser-webpack-plugin");

config.optimization = config.optimization || {};
config.optimization.minimize = true;
config.optimization.minimizer = [
    new TerserPlugin({
        terserOptions: {
            mangle: true,
            compress: false,
            output: {
                beautify: false,
            },
        },
    }),
];