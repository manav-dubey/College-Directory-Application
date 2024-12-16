/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["../../templates/**/*.{html,js}"],
  theme: {
    extend: {
     fontFamily:{
            'heading':['Poppins','sans-serif'],
            'body':['inter','sans-serif']
          }
          },
  },
  plugins: [],
}

