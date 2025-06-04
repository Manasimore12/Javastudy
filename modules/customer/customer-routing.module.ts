import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomerDashboardComponent } from './components/customer-dashboard/customer-dashboard.component';
import { PostCarComponent } from './components/post-car/post-car.component';
import { MyCarsComponent } from './components/my-cars/my-cars.component';
import { UpdateCarComponent } from './components/update-car/update-car.component';

const routes: Routes = [
  {path:"dashboard", component:CustomerDashboardComponent},
  {path:"post-car", component:PostCarComponent},
  {path:"my-cars", component:MyCarsComponent},
  {path:"car/edit/:id", component:UpdateCarComponent},

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
