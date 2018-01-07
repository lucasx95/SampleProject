import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SampleManyComponent } from './sample-many.component';
import { SampleManyDetailComponent } from './sample-many-detail.component';
import { SampleManyPopupComponent } from './sample-many-dialog.component';
import { SampleManyDeletePopupComponent } from './sample-many-delete-dialog.component';

export const sampleManyRoute: Routes = [
    {
        path: 'sample-many',
        component: SampleManyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleProjectApp.sampleMany.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sample-many/:id',
        component: SampleManyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleProjectApp.sampleMany.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sampleManyPopupRoute: Routes = [
    {
        path: 'sample-many-new',
        component: SampleManyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleProjectApp.sampleMany.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sample-many/:id/edit',
        component: SampleManyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleProjectApp.sampleMany.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sample-many/:id/delete',
        component: SampleManyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleProjectApp.sampleMany.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
