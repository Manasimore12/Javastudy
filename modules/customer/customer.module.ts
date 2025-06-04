import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomerRoutingModule } from './customer-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedNgZorroModule } from '../../shared-ng-zorro.module';
import { HttpClientModule } from '@angular/common/http';
import { RouterOutlet } from '@angular/router';


@NgModule({
  declarations: [],
  imports: [
    CustomerRoutingModule,
    ReactiveFormsModule,
    SharedNgZorroModule,
    FormsModule,
    HttpClientModule,RouterOutlet,
  ]
})
export class CustomerModule { }
