module.exports = {


  modules: [
    '@nuxtjs/axios',
  ],
    /* 需要使用 aixos必须配置以下两项axios和proxy*/
    axios: {
      prefix: '/api/',
      proxy: true
  },

  proxy: {
      '/api/': {
        target: 'http://localhost:8222',
        pathRewrite: {
          '^/api/': ''
        }
      }
  },


  // some nuxt config...
  plugins: [
    { src: '~/plugins/nuxt-swiper-plugin.js', ssr: false }
  ],

  css: [
    'swiper/dist/css/swiper.css'
  ],
  /*
  ** Headers of the page
  */
  head: {
    title: '小学数学在线教育',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '{{escape description }}' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }
    ]
  },
  /*
  ** Customize the progress bar color
  */
  loading: { color: '#3B8070' },
  /*
  ** Build configuration
  */
  build: {
    /*
    ** Run ESLint on save
    */
    extend (config, { isDev, isClient }) {
      if (isDev && isClient) {
        config.module.rules.push({
          enforce: 'pre',
          test: /\.(js|vue)$/,
          loader: 'eslint-loader',
          exclude: /(node_modules)/
        })
      }
    }
  }
}

