import {Component, Injectable, OnInit} from '@angular/core';
import {SettingsService} from "./settings.service";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {

  constructor(private settingsService:SettingsService) {
  }

  gridSize: number = 0;

  ngOnInit() {
  }

}
