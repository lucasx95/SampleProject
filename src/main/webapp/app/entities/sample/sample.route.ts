import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SampleComponent } from './sample.component';
import { SampleDetailComponent } from './sample-detail.component';
import { SamplePopupComponent } from './sample-dialog.component';
import { SampleDeletePopupComponent } from './sample-delete-dialog.component';

export const sampleRoute: Routes = [
    {
        path: 'sample',
        component: SampleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleProjectApp.sample.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sample/:id',
        component: SampleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleProjectApp.sample.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const samplePopupRoute: Routes = [
    {
        path: 'sample-new',
        component: SamplePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleProjectApp.sample.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sample/:id/edit',
        component: SamplePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleProjectApp.sample.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sample/:id/delete',
        component: SampleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleProjectApp.sample.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
