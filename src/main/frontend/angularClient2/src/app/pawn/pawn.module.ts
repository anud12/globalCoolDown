import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {PawnService} from "./pawn.service";
import {HttpClient} from "@angular/common/http";
import {HttpModule} from "@angular/http";

@NgModule({
  imports: [
    CommonModule
  ],
  providers: [PawnService]

})
export class PawnModule { }
