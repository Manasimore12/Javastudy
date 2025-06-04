import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { SharedNgZorroModule } from '../../../../shared-ng-zorro.module';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-my-cars',
  imports: [ReactiveFormsModule,
      CommonModule,
      SharedNgZorroModule,RouterModule],
  templateUrl: './my-cars.component.html',
  styleUrl: './my-cars.component.scss'
})
export class MyCarsComponent {
  cars :any =[];
    constructor(private service: CustomerService, private message:NzMessageService){ }
    ngOnInit(){
      this.getCars();
    }
    getCars(){
      this.service.getMyCars().subscribe((res)=> 
      {
         console.log(res);
         this.cars =res;
        })
    }
    deleteCar(id:number){
      this.service.deleteCar(id).subscribe((res)=>{
        this.message.success("Car deleted Successfully",{nzDuration: 5000 });
        this.getCars();
      })

    }
}
