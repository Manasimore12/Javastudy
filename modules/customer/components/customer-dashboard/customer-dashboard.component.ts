import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SharedNgZorroModule } from '../../../../shared-ng-zorro.module';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-customer-dashboard',
  imports: [ReactiveFormsModule,
      CommonModule,
      SharedNgZorroModule,RouterModule],
  templateUrl: './customer-dashboard.component.html',
  styleUrl: './customer-dashboard.component.scss'
})
export class CustomerDashboardComponent {

  cars :any =[];
  constructor(private service: CustomerService){ }
  ngOnInit(){
    this.getCars();
  }
  getCars(){
    this.service.getAllCars().subscribe((res)=> 
    {
       console.log(res);
       this.cars =res;
      })
  }
}
