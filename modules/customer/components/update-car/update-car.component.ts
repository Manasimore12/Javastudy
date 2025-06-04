import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SharedNgZorroModule } from '../../../../shared-ng-zorro.module';
import { StorageService } from '../../../../auth/services/storage/storage.service';

@Component({
  selector: 'app-update-car',
  standalone: true, 
  imports: [ReactiveFormsModule,
    CommonModule,
    SharedNgZorroModule,RouterModule],
    providers: [CustomerService],
  templateUrl: './update-car.component.html',
  styleUrl: './update-car.component.scss'
})
export class UpdateCarComponent {
  id!: number;
  existingImage: string | null = null;
  listOfBrands = ["BMW", "AUDI", "FERRARI", "TESLA", "VOLVO", "TOYOTA", "HONDA", "FORD", "NISSAN", "HYUNDAI", "LEXUS", "KIA", "HAVAL"];
  listOfType = ["Petrol", "Hybrid", "Diesel", "Electric", "CNG"];
  listOfColor = ["Red", "White", "Blue", "Black", "Orange", "Grey", "Silver"];
  listOfTransmission = ["Manual", "Automatic"];
updateCarForm!: FormGroup;
  isSpinning:boolean=false;

    constructor(private service: CustomerService,
      private activatedRoute: ActivatedRoute,
      private fb:FormBuilder,
      private router: Router
    ){ }

    ngOnInit(){
    this.id = +this.activatedRoute.snapshot.params["id"];
    this.updateCarForm= this.fb.group({
      brand:[null,[Validators.required]],
      name:[null,[Validators.required]],
      type:[null,[Validators.required]],
      transmission:[null,[Validators.required]],
      color:[null,[Validators.required]],
      year:[null,[Validators.required]],
      description:[null,[Validators.required]],
      price:[null,[Validators.required]],
      })
      this.getCar();
  };
    getCar(){
      this.service.getCarById(this.id).subscribe((res)=> 
      {
         console.log(res);
         this.existingImage = 'data:image/jpeg;base64,' +res.returnedImg;
         this.updateCarForm.patchValue(res);

        })
    }
    updateCar(){

    }
}
