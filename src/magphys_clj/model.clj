(ns magphys-clj.model)

(defrecord StarFormationHistory [ 
   nage ; number of time steps
   age ; age of the galaxy
   sfr ; star formation rate (function of age)
   sfrav ; star formation rate averaged over 1e+6, 1e+7, 1e+8, 1e+9 and 2e+9 yrs
   ]
)

(defrecord ModelParams [
  tform ; age of the galaxy (in yr)
  gamma ; star formation timescale (in Gyr^-1)
  zmet ; metallicity (in solar units)
  tauv0 ; total V-band optical depth seen by young stars in birth clouds
  mu ; fraction of tauv0 contributed by the ambient (diffuse) ISM
  nburst ; number of random bursts
  mstr1 ; effective stellar mass
  mstr0 ; total mass of stars formed
  mstry ; mass of stars in birth clouds
  tlastburst ; time of last burst of star formation
  fburst ; fraction of total stellar mass formed in bursts (array of 5)
  ftot ; (array of 5)
  age_wm ; mass weighted age
  age_wr ; r-band weighted age
  lha ; H-alpha line luminosity
  lhb ; H-beta line luminosity
  ldtot ; total luminosity absorbed by dust (birth clouds + ambient ISM)
  fmu ; fraction of total dust luminosity contributed by the ambient ISM
  fbc ; fraction of total V-band effective optical depth in the birth clouds contributed by dust in the HII region
]
)

(defrecord SED [
	fprop ;attenuated stellar spectrum (L_lambda in L_sun/A)
	fprop0 ;  unattenuated stellar spectrum (L_lambda in L_sun/A)
	model-params
    star-formation-history
   ]
)

(defn make-star-formation-history [params-list]
    (let [nage (int (first params-list))
          partitions (partition-all nage (rest params-list))]
	  (StarFormationHistory. nage
	                       (first partitions)
						   (second partitions) (nth partitions 2)))
)

; tform,gamma,zmet,tauv0,mu,nburst, mstr1,mstr0,mstry,tlastburst,(fburst(i),i=1,5),(ftot(i),i=1,5),age_wm,age_wr,aux,aux,aux,aux,lha,lhb,aux,aux,ldtot,fmu,fbc
(defn make-model-params [params-list]
   (ModelParams. (first params-list) 
                 (nth params-list 1) 
                 (nth params-list 2)
                 (nth params-list 3) 
                 (nth params-list 4) 
                 (nth params-list 5) 
                 (nth params-list 6)
                 (nth params-list 7) 
                 (nth params-list 8) 
                 (nth params-list 9)
                 (take 5 (drop 10 params-list))
                 (take 5 (drop 15 params-list))
                 (nth params-list 20) 
                 (nth params-list 21)
                 (nth params-list 26)
                 (nth params-list 27)
                 (nth params-list 30) 
                 (nth params-list 31)
                 (nth params-list 32)
   )
)

(defn make-sed-model [fprop fprop0 model-params star-formation-history]
  (SED. fprop fprop0 model-params star-formation-history)
)