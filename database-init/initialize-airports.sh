#!/usr/bin/env bash

Bold='\033[1m'   # Bold
NC='\033[0m'     # No Color

# To change this banner, go to http://patorjk.com/software/taag/#p=display&f=Big&t=Lunatech%0AAirport%0AAssessment
cat << "EOF"
  _                                _                   _                   
 | |                              | |                 | |                  
 | |       _   _   _ __     __ _  | |_    ___    ___  | |__                
 | |      | | | | | '_ \   / _` | | __|  / _ \  / __| | '_ \               
 | |____  | |_| | | | | | | (_| | | |_  |  __/ | (__  | | | |              
 |______|  \__,_| |_| |_|  \__,_|  \__|  \___| _\___| |_| |_|              
     /\     (_)                               | |                          
    /  \     _   _ __   _ __     ___    _ __  | |_                         
   / /\ \   | | | '__| | '_ \   / _ \  | '__| | __|                        
  / ____ \  | | | |    | |_) | | (_) | | |    | |_                     _   
 /_/ /\ \_\ |_| |_|    | .__/   \___/  |_|     \__|                   | |  
    /  \     ___   ___ | |___   ___   ___   _ __ ___     ___   _ __   | |_ 
   / /\ \   / __| / __||_/ _ \ / __| / __| | '_ ` _ \   / _ \ | '_ \  | __|
  / ____ \  \__ \ \__ \ |  __/ \__ \ \__ \ | | | | | | |  __/ | | | | | |_ 
 /_/    \_\ |___/ |___/  \___| |___/ |___/ |_| |_| |_|  \___| |_| |_|  \__|                                                                          


EOF

echo -e "${Bold}Initializing schema...${NC}"
psql --host=postgres --username=postgres -w -d lunatech_airport -f ./schema.sql &&
echo -e "\n${Bold}Initializing countries...${NC}"
psql --host=postgres --username=postgres -w -d lunatech_airport -c "\copy countries(id, code, name, continent, wikipedia_link, keywords) FROM 'countries.csv' DELIMITER ',' CSV HEADER"
echo -e "\n${Bold}Initializing airports...${NC}"
psql --host=postgres --username=postgres -w -d lunatech_airport -c "\copy airports(id, ident, type, name, latitude_deg, longitude_deg, elevation_ft, continent, iso_country, iso_region, municipality, scheduled_service, gps_code, iata_code, local_code, home_link, wikipedia_link, keywords) FROM 'airports.csv' DELIMITER ',' CSV HEADER"
echo -e "\n${Bold}Initializing runways...${NC}"
psql --host=postgres --username=postgres -w -d lunatech_airport -c "\copy runways(id, airport_ref, airport_ident, length_ft, width_ft, surface, lighted, closed, le_ident, le_latitude_deg, le_longitude_deg, le_elevation_ft, le_heading_degT, le_displaced_threshold_ft, he_ident, he_latitude_deg, he_longitude_deg, he_elevation_ft, he_heading_degT, he_displaced_threshold_ft) FROM 'runways.csv' DELIMITER ',' CSV HEADER"

# To change this banner, go to http://patorjk.com/software/taag/#p=display&f=Big&t=DB%20ready%20to%20use!
cat << "EOF"
  _____  ____                       _         _                         _ 
 |  __ \|  _ \                     | |       | |                       | |
 | |  | | |_) |  _ __ ___  __ _  __| |_   _  | |_ ___    _   _ ___  ___| |
 | |  | |  _ <  | '__/ _ \/ _` |/ _` | | | | | __/ _ \  | | | / __|/ _ \ |
 | |__| | |_) | | | |  __/ (_| | (_| | |_| | | || (_) | | |_| \__ \  __/_|
 |_____/|____/  |_|  \___|\__,_|\__,_|\__, |  \__\___/   \__,_|___/\___(_)
                                       __/ |                              
                                      |___/                               
EOF
