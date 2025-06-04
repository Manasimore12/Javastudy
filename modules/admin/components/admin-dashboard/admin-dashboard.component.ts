import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { SharedNgZorroModule } from '../../../../shared-ng-zorro.module';

@Component({
  selector: 'app-admin-dashboard',
  imports: [ReactiveFormsModule,
      CommonModule,
      SharedNgZorroModule,RouterModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.scss'
})
export class AdminDashboardComponent {
  cars :any =[];
    constructor(private service: AdminService){ }
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
