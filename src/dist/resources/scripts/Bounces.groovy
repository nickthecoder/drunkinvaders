// A simple interface used by AlienShip to look for other things to bounce against!
// AlienShip, Shild and Ship all implement Bounces.
// Note, Ship only causes the AlienShip to bounce when Ship is shielded.

interface Bounces {
    void bounce( double impact )
}

