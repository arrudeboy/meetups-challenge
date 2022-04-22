from fastapi import APIRouter, Depends, HTTPException

meetups = APIRouter(prefix='/meetups')

@meetups.get("/{id}/beerPacksNeeded")
def beer_packs_needed(id: int):