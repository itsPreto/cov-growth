# cov-growth

This app is a "Covid Data-Pack Analyser" or at least that's what it'll be ... eventually. 

1- I'm consuming data from a public backend (no key or headers needed) -> https://api.covid19api.com

2- Parsing the various Json responses into their appropiate models (Gson, Coroutines, Retrofit, OkHttp, LiveData, 

3- Storing them for a later retrieval (Room/ViewModel/Repository). 

The Masterplan: 
  - To use some slick math skills to put all the data to good use and visualize it in all sorts of graphs using MPChart.
  
CI/CD: tbd
 
Workflow: 
  - There are 2 main branches: 'master' and 'develop'. 
  - We will each work on our workspace branch and raise PR's to 'develop':
      **each PR needs to include all of us as reviewrs.
