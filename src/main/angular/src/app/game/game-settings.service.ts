import {Injectable} from '@angular/core';

interface SettingsModel {
    minSelectRadius: number
}


@Injectable({
    providedIn: 'root'
})
export class GameSettingsService implements SettingsModel {
    minSelectRadius = 7;

    constructor() {
    }
}
