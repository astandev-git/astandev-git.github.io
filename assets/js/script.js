/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~OLD WORKING CODE~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

/**/ 
function showList(list) {
  if(event) event.preventDefault(); // Stop page refresh
  document.getElementById(list).classList.toggle("hiddenList");
}

/**/ 
function checkProfessional() {
  if(event) event.preventDefault(); // Stop page refresh
  window.open("./index/professional.html", "_blank", rel="noopener noreferrer");
}

/**/ 
function checkPersonal() {
  if(event) event.preventDefault(); // Stop page refresh
  let inputPwd = document.getElementById("personalInput").value;
  let correctPwd = "stangit";
  if (inputPwd === correctPwd) {
    window.open("./index/personal.html", "_blank", rel="noopener noreferrer")
  } else {
    alert("Incorrect password!");
  }
}

/**/ 
function checkTesting() {
  if(event) event.preventDefault(); // Stop page refresh
  let inputPwd = document.getElementById("testingInput").value;
  let correctPwd = "standev";
  if (inputPwd === correctPwd) {
    window.open("./index/testing.html", "_blank", rel="noopener noreferrer")
  } else {
    alert("Incorrect password!");
  }
}


/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~NEW CODE~~~~~~~~~~~~~~~~~~~~~~~~~~~~  */


/* ~~~~~~ Main Function - on page load ~~~~~~ */

$(document).ready(function() {    


  /* ~~~~~~ VI1: Fetch IP Address ~~~~~~ */
  
  $.ajax({
    url: "https://api.ipify.org?format=jsonp",        
    dataType: "jsonp",           
    jsonp: "callback",           
    success: function(data) {
      $("#ipAddr").html("<strong>" + data.ip + "</strong>");
    },
    error: function(xhr, status, error) {
      console.log("IP Error:", error);
      $("#ipAddr").text("Connection error. Check ad-blockers.");
    }
  });


  /* ~~~~~~ Secondary Function - on successful location retrieval ~~~~~~ */

  async function success(position) {
	  const lat = position.coords.latitude;
    const long = position.coords.longitude;
    const loc = " Lat: " + lat + " Long: " + long;
    const apiKey = "AIzaSyBJQ-2KxsKf27QgjX8Dc6wGxc_g9faWfxA"


    /* ~~~~~~ VI2b: Store Location ~~~~~~ */

    $("#location").text(loc);
    if (window.localStorage) {
  		localStorage.setItem('lat', lat);
      localStorage.setItem('long', long);
      localStorage.setItem('whereIwas', loc);
  		$("#status").text("Stored in local storage!");    
	  } else {
  		alert("window.localStorage not working");
	  }		


    /* ~~~~~~ VI4: Get Address ~~~~~~ */

    getAddress(lat, long)
      .then(data => $("#address").text("Address found: " + data.display_name))
      .catch(err => console.error(err));


    /* ~~~~~~ VI7: Weather API ~~~~~~ */

    $.ajax({
      url: `https://weather.googleapis.com/v1/currentConditions:lookup`,
      type: 'GET',
      data: {
        key: apiKey,
        'location.latitude': lat,
        'location.longitude': long,
        'unitsSystem': 'IMPERIAL'
      },
      success: function(data) {
        $("#timeZone").text(data.timeZone.id);
        $("#weather").text("Current Temp: " + data.temperature.degrees + "°");
        $("#weather").append("<br> Daytime: " + data.isDaytime);
        $("#weather").append("<br> Conditions: " + data.weatherCondition.description.text);
        $("#weather").append("<br> Feels like: " + data.feelsLikeTemperature.degrees + "°");
        $("#weather").append("<br> Humidity: " + data.relativeHumidity + "%");
        $("#weather").append("<br> Wind Speed: " + data.wind.speed.value + " mph");
        $("#weather").append("<br> Wind Direction: " + data.wind.direction.degrees + "°");
        $("#weather").append("<br> Precipitation Chance: " + data.precipitation.probability.percent + "%");
      },
      error: function(xhr) {
        console.error("Weather API failed:", xhr.statusText);            
      }
    });  

    
    /* ~~~~~~ Get Country from Address ~~~~~~ */
    
    async function getCountryFromAddress(lat,lng) {
      const url = `https://api.bigdatacloud.net/data/reverse-geocode-client/?latitude=${lat}&longitude=${lng}&localityLanguage=en`;

      try {
        const response = await fetch(url);
        const data = await response.json();

        if (data.countryCode) {
          console.log("Country Code Found:", data.countryCode);
          return data.countryCode;
        } else {
          console.error("Geocode failed:", data.status);
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    }


    /* ~~~~~~ VI6: Country Facts API ~~~~~~ */
  
    async function getCountryDetails(countryCode) {
  
      const service = `alpha/${countryCode}`;
      const fields = "name,cca3,flags,population,area,region,capital,languages,currencies";
      const url = `https://restcountries.com/v3.1/${service}?fields=${fields}`;

      try {
        const response = await fetch(url);
        const country = await response.json();
        
        $("#countryFacts").append("<br> Flag: " + `<img src="${country.flags.png}" alt="Flag" style="width: 100px; vertical-align: middle; border: 1px solid #ddd;">`);
        $("#countryFacts").append("<br> Name: " + country.name.common);
        $("#countryFacts").append("<br> Code: " + country.cca3);
        $("#countryFacts").append("<br> Population: " + country.population);
        $("#countryFacts").append("<br> Area: " + country.area + " km²");
        $("#countryFacts").append("<br> Region: " + country.region);
        $("#countryFacts").append("<br> Capital: " + country.capital);

        $("#countryFacts").append("<br> Languages: ");
        const langList = Object.values(country.languages).join(", ");
        $("#countryFacts").append(langList);
    
        $("#countryFacts").append("<br> Currencies: ");
        const currList = Object.values(country.currencies).map(currency => currency.name).join(", ");
        $("#countryFacts").append(currList);
      } catch (error) {
        console.error("Error fetching country data:", error);
      }
    }
  
    const countryCode = await getCountryFromAddress(lat,long);
    getCountryDetails(countryCode);

  } // End of success function

  function fail(msg) {
    $("#status").text("Geolocation failed: " + msg);
  }


  /* ~~~~~~ VI2a: Fetch Location - calls Secondary Function on success ~~~~~~ */

  if (navigator.geolocation) {
  	navigator.geolocation.getCurrentPosition(success,fail);
  } else {
  	alert("Geolocation not supported");
  }


  /* ~~~~~~ VI3: Reverse Geocoding (fairly sure I'm not caching and checking correctly) ~~~~~~ */
  
  var lat = localStorage.getItem('lat');
  var long = localStorage.getItem('long');
  
  // Setup local cache storage (required by Nominatim)
  const geoLocal = JSON.parse(localStorage.getItem('nominatim_cache')) || {}; 

  function getAddress(lat, lon) {

    const cacheKey = `${lat},${lon}`; 
  
    // Check for cached data (required by Nominatim)
    if (geoLocal[cacheKey]) {
      console.log("Found in cache:", geoLocal[cacheKey]);
      return Promise.resolve(geoLocal[cacheKey]);
    }
  
    // Rate limiter (required by Nominatim - rate limit of 1 request per second)
    const lastRequestTime = localStorage.getItem('last_geo_request_time') || 0;
    const timeSinceLastRequest = Date.now() - lastRequestTime;

    if (timeSinceLastRequest < 1000) {
      alert("Rate limit exceeded. Waiting...");
      return new Promise((resolve) => {
        setTimeout(() => {
          localStorage.setItem('last_geo_request_time', Date.now());
          getAddress(lat, lon).then(resolve);
        }, 1000 - timeSinceLastRequest);
      });
    }

    const now = Date.now();

    // Get address if not cached
    return $.ajax({
      url: 'https://nominatim.openstreetmap.org/reverse',
      type: 'GET',
      data: {
          lat: lat,
          lon: lon,
          format: 'jsonv2', 
          addressdetails: 1
      },
      headers: {        
          'User-Agent': 'PersonalGithubPage/1.0 (stancilat@yahoo.com)'
      }
    }).then(data => {
      
      // Update the timestamp to enforce the 1s limit on the next call
      localStorage.setItem('last_geo_request_time', Date.now());

      // Save to cache (Required by Nominatim)
      geoLocal[cacheKey] = data;
      localStorage.setItem('nominatim_cache', JSON.stringify(geoLocal));
        
      return data;
    });
  }
  

  /* ~~~~~~ VI4: Initialize Google Maps ~~~~~~ */

  (g=>{var h,a,k,p="The Google Maps JavaScript API",c="google",l="importLibrary",q="__ib__",m=document,b=window;b=b[c]||(b[c]={});var d=b.maps||(b.maps={}),r=new Set,e=new URLSearchParams,u=()=>h||(h=new Promise(async(f,n)=>{await (a=m.createElement("script"));e.set("libraries",[...r]+"");for(k in g)e.set(k.replace(/[A-Z]/g,t=>"_"+t[0].toLowerCase()),g[k]);e.set("callback",c+".maps."+q);a.src=`https://maps.${c}apis.com/maps/api/js?`+e;d[q]=f;a.onerror=()=>h=n(Error(p+" could not load."));a.nonce=m.querySelector("script[nonce]")?.nonce||"";m.head.append(a)}));d[l]?console.warn(p+" only loads once. Ignoring:",g):d[l]=(f,...n)=>r.add(f)&&u().then(()=>d[l](f,...n))})({
    key: "AIzaSyBJQ-2KxsKf27QgjX8Dc6wGxc_g9faWfxA",
    v: "weekly",
  });

  let map;  

  async function initMap() {

    const storedLat = parseFloat(localStorage.getItem('lat'));
    const storedLong = parseFloat(localStorage.getItem('long'));

    if (isNaN(storedLat) || isNaN(storedLong)) {
      $("#status").text("No saved location found for the map.");
      return;
    }

    let center =  { lat: storedLat, lng: storedLong };

    // Import the needed libraries
    await google.maps.importLibrary('maps');
    await google.maps.importLibrary("marker");

    map = new google.maps.Map(document.getElementById("map"), {
      center,
      zoom: 12,
      mapId: "DEMO_MAP_ID",
    });

    /*const marker = new google.maps.marker.AdvancedMarkerElement({
      map,
      position: center,
    }); */
  }

  var mapCanvas = document.getElementById('map'); 
  
  // Only run initMap if the div exists
  if (mapCanvas) {   
    initMap();
  }

});   // End of document ready function


/* ~~~~~~ DF1: guessAge function ~~~~~~ */

async function guessAge() {
  event.preventDefault();

  const name = document.getElementById('nameInput').value;
  
  const response = await fetch(`https://api.agify.io?name=${name}`);
  const data = await response.json();

  $("#ageOutput").text(data.age);
}


/* ~~~~~~ DF2: getJoke function ~~~~~~ */

async function getJoke() {  
  const response = await fetch(`https://v2.jokeapi.dev/joke/Any?blacklistFlags=nsfw,religious,racist,sexist,explicit&type=single&amount=10`);
  const data = await response.json();

  $("#joke").empty();

  data.jokes.forEach(joke => {
    $("#joke").append("Category: " + joke.category + "<br>");
    $("#joke").append("Joke: " + joke.joke + "<br>");
    $("#joke").append("<br>");
  });
}

getJoke();


/* ~~~~~~ DF3: APOD function ~~~~~~ */

async function getAPOD() {
  const response = await fetch(`https://api.nasa.gov/planetary/apod?api_key=EUxNJ3bVARbRTfWYZrEpAx6fMa4HJyL9RQyOL9N2`);
  const data = await response.json();

  $("#apod").empty();
  $("#apod").append(`<h3>${data.title}</h3>`);
  $("#apod").append(`<img src="${data.url}" alt="${data.title}" style="max-width: 100%;">`);
  $("#apod").append(`<p>${data.explanation}</p>`);
}

getAPOD();


/* ~~~~~~ DF4: Useless Fact function ~~~~~~ */

async function getUselessFact() {
  const responseRandom = await fetch(`https://uselessfacts.jsph.pl/api/v2/facts/random`);
  const dataRandom = await responseRandom.json();

  const responseToday = await fetch(`https://uselessfacts.jsph.pl/api/v2/facts/today`);
  const dataToday = await responseToday.json();

  $("#uselessFact").empty();
  $("#uselessFact").append(`<p>Random Fact: ${dataRandom.text}</p>`);
  $("#uselessFact").append(`<p>Today's Fact: ${dataToday.text}</p>`);
}

getUselessFact();


/* ~~~~~~ M1: Music Player ~~~~~~ */

// Only run if the music player exists  
if (document.getElementById("play")) {

  const playlistSongs = document.getElementById("playlist-songs");
  const playButton = document.getElementById("play");
  const pauseButton = document.getElementById("pause");
  const nextButton = document.getElementById("next");
  const previousButton = document.getElementById("previous");
  const shuffleButton = document.getElementById("shuffle");

  const allSongs = [
    {
      id: 0,
      title: "01 - Jumaira Drive",
      artist: "Manoa",
      duration: "6:26",
      src: "./index/files/music/01 - Manoa - Jumaira Drive.mp3",
    },
    {
      id: 1,
      title: "02 - Spring",
      artist: "Plasma",
      duration: "3:58",
      src: "./index/files/music/02 - Plasma - Spring.mp3",
    },
    {
      id: 2,
      title: "03 - Take On Me",
      artist: "Kid Coconutz",
      duration: "6:10",
      src: "./index/files/music/03 - Kid Coconutz - Take On Me.mp3",
    },
    {
      id: 3,
      title: "04 - Marching The Hate Machines (Into The Sun) (Feat- The Flaming Lips)",
      artist: "Theivery Corporation",
      duration: "4:01",
      src: "./index/files/music/04 - Theivery Corporation - Marching The Hate Machines (Into The Sun) (Feat- The Flaming Lips).mp3",
    },
    {
      id: 4,
      title: "05 - Le Monde",
      artist: "Theivery Corporation",
      duration: "3:11",
      src: "./index/files/music/05 - Theivery Corporation - Le Monde.mp3",
    },
    {
      id: 5,
      title: "06 - Theivery Corporation - Lebanese Blonde",
      artist: "Theivery Corporation",
      duration: "4:48",
      src: "./index/files/music/06 - Theivery Corporation - Lebanese Blonde.mp3",
    },
    {
      id: 6,
      title: "07 - Center Of The Sun [Solarstone Remix]",
      artist: "Conjure One",
      duration: "6:09",
      src: "./index/files/music/07 - Conjure One - Center Of The Sun [Solarstone Remix].mp3",
    },
    {
      id: 7,
      title: "08 - Tears From The Moon (Carmen Rizzo Mix) w. Sinead O' Connor",
      artist: "Conjure One",
      duration: "4:33",
      src: "./index/files/music/08 - Conjure One - Tears From The Moon (Carmen Rizzo Mix) w. Sinead O' Connor.mp3",
    },
    {
      id: 8,
      title: "09 - World On Fire (Junkie Xl Club Mix)",
      artist: "Sarah Mclachlan",
      duration: "4:40",
      src: "./index/files/music/09 - Sarah Mclachlan - World On Fire (Junkie Xl Club Mix).mp3",
    },
    {
      id: 9,
      title: "10 - Birthday",
      artist: "Junior Boys",
      duration: "4:12",
      src: "./index/files/music/10 - Junior Boys - Birthday.mp3",
    },
  ];

  const audio = new Audio();
  let userData = {
    songs: [...allSongs],
    currentSong: null,
    songCurrentTime: 0,
  };

  const playSong = (id) => {
    const song = userData?.songs.find((song) => song.id === id);
    audio.src = song.src;
    audio.title = song.title;

    if (userData?.currentSong === null || userData?.currentSong.id !== song.id) {
      audio.currentTime = 0;
    } else {
      audio.currentTime = userData?.songCurrentTime;
    }
    userData.currentSong = song;
    playButton.classList.add("playing");

    highlightCurrentSong();
    setPlayerDisplay();
    setPlayButtonAccessibleText();
    audio.play();
  };

  const pauseSong = () => {
    userData.songCurrentTime = audio.currentTime;
  
    playButton.classList.remove("playing");
    audio.pause();
  };

  const playNextSong = () => {
    if (userData?.currentSong === null) {
      playSong(userData?.songs[0].id);
    } else {
      const currentSongIndex = getCurrentSongIndex();
      const nextSong = userData?.songs[currentSongIndex + 1];

      playSong(nextSong.id);
    }
  };

  const playPreviousSong = () => {
     if (userData?.currentSong === null) return;
    else {
      const currentSongIndex = getCurrentSongIndex();
      const previousSong = userData?.songs[currentSongIndex - 1];

      playSong(previousSong.id);
    }
  };

  const shuffle = () => {
    userData?.songs.sort(() => Math.random() - 0.5);
    userData.currentSong = null;
    userData.songCurrentTime = 0;

    renderSongs(userData?.songs);
    pauseSong();
    setPlayerDisplay();
    setPlayButtonAccessibleText();
  };

  const deleteSong = (id) => {
    if (userData?.currentSong?.id === id) {
      userData.currentSong = null;
      userData.songCurrentTime = 0;

      pauseSong();
      setPlayerDisplay();
    }

    userData.songs = userData?.songs.filter((song) => song.id !== id);
    renderSongs(userData?.songs); 
    highlightCurrentSong(); 
    setPlayButtonAccessibleText(); 

  };

  const setPlayerDisplay = () => {
    const playingSong = document.getElementById("player-song-title");
    const songArtist = document.getElementById("player-song-artist");
    const currentTitle = userData?.currentSong?.title;
    const currentArtist = userData?.currentSong?.artist;

    playingSong.textContent = currentTitle ? currentTitle : "";
    songArtist.textContent = currentArtist ? currentArtist : "";
  };

  const highlightCurrentSong = () => {
    const playlistSongElements = document.querySelectorAll(".playlist-song");
    const songToHighlight = document.getElementById(
      `song-${userData?.currentSong?.id}`
    );

    playlistSongElements.forEach((songEl) => {
      songEl.removeAttribute("aria-current");
    });

    if (songToHighlight) songToHighlight.setAttribute("aria-current", "true");
  };

  const renderSongs = (array) => {
    const songsHTML = array
      .map((song)=> {
        return `
        <li id="song-${song.id}" class="playlist-song">
          <button class="playlist-song-info" onclick="playSong(${song.id})">
            <span class="playlist-song-title">${song.title}</span>
            <span class="playlist-song-artist">${song.artist}</span>
            <span class="playlist-song-duration">${song.duration}</span>
          </button>
          <button onclick="deleteSong(${song.id})" class="playlist-song-delete" aria-label="Delete ${song.title}">
            <svg width="20" height="20" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg"><circle cx="8" cy="8" r="8" fill="#4d4d62"/>
            <path fill-rule="evenodd" clip-rule="evenodd" d="M5.32587 5.18571C5.7107 4.90301 6.28333 4.94814 6.60485 5.28651L8 6.75478L9.39515 5.28651C9.71667 4.94814 10.2893 4.90301 10.6741 5.18571C11.059 5.4684 11.1103 5.97188 10.7888 6.31026L9.1832 7.99999L10.7888 9.68974C11.1103 10.0281 11.059 10.5316 10.6741 10.8143C10.2893 11.097 9.71667 11.0519 9.39515 10.7135L8 9.24521L6.60485 10.7135C6.28333 11.0519 5.7107 11.097 5.32587 10.8143C4.94102 10.5316 4.88969 10.0281 5.21121 9.68974L6.8168 7.99999L5.21122 6.31026C4.8897 5.97188 4.94102 5.4684 5.32587 5.18571Z" fill="white"/></svg>
          </button>
        </li>
        `;
      })
      .join("");

    if (playlistSongs) { 
      playlistSongs.innerHTML = songsHTML;
    }

    if (userData?.songs.length === 0) {
      const resetButton = document.createElement("button");
      const resetText = document.createTextNode("Reset Playlist");

      resetButton.id = "reset";
      resetButton.ariaLabel = "Reset playlist";
      resetButton.appendChild(resetText);
      playlistSongs.appendChild(resetButton);

      resetButton.addEventListener("click", () => {
        userData.songs = [...allSongs];

        renderSongs(sortSongs()); 
        setPlayButtonAccessibleText();
        resetButton.remove();
      });

    };

  };

  const setPlayButtonAccessibleText = () => {
    const song = userData?.currentSong || userData?.songs[0];

    if (playButton) {
      playButton.setAttribute(
        "aria-label",
        song?.title ? `Play ${song.title}` : "Play"
      );
    }

  };

  const getCurrentSongIndex = () => userData?.songs.indexOf(userData?.currentSong);

  var mPlayer = document.getElementById('music');

  playButton?.addEventListener("click", () => {
    if (userData?.currentSong === null) {
      playSong(userData?.songs[0].id);
    } else {
      playSong(userData?.currentSong.id);
    }
  });

  pauseButton?.addEventListener("click", pauseSong);

  nextButton?.addEventListener("click", playNextSong);

  previousButton?.addEventListener("click", playPreviousSong);

  shuffleButton?.addEventListener("click", shuffle);

  audio.addEventListener("ended", () => {
    const currentSongIndex = getCurrentSongIndex();
    const nextSongExists = userData?.songs[currentSongIndex + 1] !== undefined;

    if (nextSongExists) {
      playNextSong();
    } else {
      userData.currentSong = null;
      userData.songCurrentTime = 0;  
      pauseSong();
      setPlayerDisplay();
      highlightCurrentSong();
      setPlayButtonAccessibleText();
    }
  });

  const sortSongs = () => {
    userData?.songs.sort((a,b) => {
      if (a.title < b.title) {
        return -1;
      }

      if (a.title > b.title) {
        return 1;
      }

      return 0;
    });

    return userData?.songs;
  };

  renderSongs(sortSongs());
  setPlayButtonAccessibleText();
  playSong(userData?.songs[0].id);            // Autoplay the first song in the playlist

}


/* ~~~~~~ MODAL ~~~~~~ */

var modal = (function() {
	var $window = $(window),
  	$modal = $('<div class="modal"/>'),
	  $content = $('<div class="modal-content"/>'),
  	$close = $('<button role="button" class="modal-close">close</button>');
	
    $modal.append($content, $close);
  
    $close.on('click', function(e) {
	    e.preventDefault();
  	  modal.close();
    });

  return {
	  center: function() {
		
		  var top = Math.max($window.height() -   // Distance from top and left to center of modal
			  $modal.outerHeight(), 0) / 2;
		  var left = Math.max($window.width() -
			  $modal.outerWidth(), 0) / 2;
		
		  $modal.css({                            // Set CSS for the modal
			  top: top + $window.scrollTop(),
  			left: left + $window.scrollLeft(),
        backgroundColor: 'slategray',
        padding: '10px',
        color: 'white',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center'
	  	});
	  }, 

    open: function(settings) {
  	  $content.empty().append(settings.content);
    	$modal.css({
		    width: settings.width || 'auto',
		    height: settings.height || 'auto'
    	}).appendTo('body');
	    modal.center();
	    $(window).on('resize', modal.center);
    },
  
    close: function() {
  		$content.empty();                       // Remove content from the modal window
	    $modal.detach();                        // Remove modal window from the page
	    $(window).off('resize', modal.center);  // Remove event handler
    }

  };
})();

$('.document a').on('click', function(e) {
  e.preventDefault();

  var parentElement = $(this).closest('.document');
  var parentId = parentElement.attr('id'); 
    
  if (parentId) {
    var iframeId = '#' + parentId.replace('document-', 'document-content-');
    var $content = $(iframeId);

    if ($content.length) {
      $content.show(); 

      modal.open({
        content: $content,
        width: '90%',
        height: '80%'
      });

      // Refresh the source to fix the blank/white box issue
      $content.attr('src', $content.attr('src'));
    }
  }
});


/* ~~~~~~ TAB PANEL ~~~~~~ */

$(function() {
  $('.tab-list').each(function(){                   // Find lists of tabs
    var $this = $(this);                            // Store list
    var $tab = $this.find('li.active');             // Get the active list item
    var $link = $tab.find('a');                     // Get link from active tab
    var $panel = $($link.attr('href'));             // Get active panel

    $this.on('click', '.tab-control', function(e) { // When clicked
      e.preventDefault();                           // Prevent link behavior
      var $link = $(this),                          // Store the current link
        id = this.hash;                             // Get href of clicked tab

      // Check if the parent LI is already active
      if (id && !$link.parent().is('.active')) {    // If not currently active
        $panel.removeClass('active');               // Make panel inactive
        $tab.removeClass('active');                 // Make tab inactive

        $panel = $(id).addClass('active');          // Make new panel active
        $tab = $link.parent().addClass('active');   // Make new tab active
      }
    });
  });
});


/* ~~~~~~ YouTube API ~~~~~~ */

// This code loads the IFrame Player API code asynchronously.
var tag = document.createElement('script');
tag.src = "https://www.youtube.com/iframe_api";

var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

var player;
var done = false;

function onYouTubeIframeAPIReady() {
  player = new YT.Player('player', {
    height: '390',
    width: '640',    
    videoId: 'vtWcfHR2LAc',                             // MUST be a valid video ID.
    playerVars: {
      'playsinline': 1,
      'autoplay': 1
    },
    events: {
      'onReady': onPlayerReady,
      'onStateChange': onPlayerStateChange
    }
  });
}

function onPlayerReady(event) {

  // Setup the link listeners
  const buttons = document.querySelectorAll('.playlist-btn');
  buttons.forEach(button => {
    button.addEventListener('click', (e) => {
      e.preventDefault();
      done = true;
      const newListId = button.getAttribute('data-list');
      console.log("Switching to:", newListId);
      player.stopVideo();                             // Force player into a clean state first
      player.cuePlaylist({
        listType: 'playlist',
        list: newListId,
        index: 0,
        startSeconds: 0
      });
      setTimeout(() => player.playVideo(), 500);       // Give cuePlaylist time to resolve
      player.playVideo();                              // Explicitly start it
    });
  });

  // Load the playlist over the anchor video
  event.target.loadPlaylist({
    listType: 'playlist',
    list: 'PLeOglbcXFmV2frZ4i7IBOLEyLCOLeDuq' ,
    index: 0,
    startSeconds: 0
  });
}

function onPlayerStateChange(event) {
  if (event.data == YT.PlayerState.PLAYING && !done) {
    setTimeout(stopVideo, 15000);
    done = true;
  }
}

function stopVideo() {
  player.stopVideo();
}


/* ~~~~~~ Photo Viewer ~~~~~~ */

var request;
var $current;
var cache = {};
var $frame = $('#photo-viewer');
var $thumbs = $('#thumbnails a');

// Cross-fade images
function crossfade($img) {
	if ($current) {
		$current.stop().fadeOut('slow');
	}
 
	$img.stop().fadeTo('slow', 1);
	$current = $img;
}

// Set-up, cache, and loading image
$(document).on('click', '#thumbnails a', function(e) {
	var $img;
	var src = this.href;
	var request = src;
	e.preventDefault();
	$thumbs.removeClass('active');
	$(this).addClass('active');
	if (cache.hasOwnProperty(src)) {
		if (cache[src].isLoading === false) {
			crossfade(cache[src].$img);
		}
	} else {
		$img = $('<img/>');
		cache[src] = {
			$img: $img,
			isLoading: true
		};
    $img.on('load', function() {
      cache[src].isLoading = false;
      if (request === src) {
        crossfade($img);
      }
    });
    $frame.append($img);
    $img.attr('src', src);                      // Must come after .on('load') to avoid race condition
  }
})

$('#thumbnails a').on('click', function() {
  const title = $(this).attr('title');
  $('#photo-label').text(title);
});
$('#thumbnails a.active').trigger('click');


/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~TEST AREA~~~~~~~~~~~~~~~~~~~~~~~~~~~~  */
/*
*/


/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~OLD CODE~~~~~~~~~~~~~~~~~~~~~~~~~~~~  */
/*
*/

/*
// Get the textbox element
const form = document.getElementById('myform');
//const inputform = document.getElementById('inputform');

// Add an event listener for the 'keydown' event
form.addEventListener('keydown', function(event) {
 
  // Check if the ENTER key (key code 13) is pressed
  if (event.key === 'Enter') {
 
  // Prevent the default action (e.g., new line in a textarea)
  event.preventDefault();

  // Submit the form
  document.getElementById('myForm').submit();
  }

}); */  