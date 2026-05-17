import { Routes } from '@angular/router';
import { LayoutComponent } from './layout/layout.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { EmployesComponent } from './pages/employes/employes.component';
import { ProjetsComponent } from './pages/projets/projets.component';
import { TachesComponent } from './pages/taches/taches.component';
import { RessourcesComponent } from './pages/ressources/ressources.component';
import { RapportsComponent } from './pages/rapports/rapports.component';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'employes', component: EmployesComponent },
      { path: 'projets', component: ProjetsComponent },
      { path: 'taches', component: TachesComponent },
      { path: 'ressources', component: RessourcesComponent },
      { path: 'rapports', component: RapportsComponent }
    ]
  }
];
