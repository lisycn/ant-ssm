module.exports = function(webpackConfig) {
  webpackConfig.babel.plugins.push(['antd', {style: true}]);

  // Fix ie8 compatibility
  webpackConfig.module.loaders.unshift({
    test: /\.jsx?$/,
    loader: 'es3ify-loader',
  });

  return webpackConfig;
};
