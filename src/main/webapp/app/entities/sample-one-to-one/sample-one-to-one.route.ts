import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SampleOneToOneComponent } from './sample-one-to-one.component';
import { SampleOneToOneDetailComponent } from './sample-one-to-one-detail.component';
import { SampleOneToOnePopupComponent } from './sample-one-to-one-dialog.component';
import { SampleOneToOneDeletePopupComponent } from './sample-one-to-one-delete-dialog.component';

export const sampleOneToOneRoute: Routes = [
    {
        path: 'sample-one-to-one',
        component: SampleOneToOneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleProjectApp.sampleOneToOne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sample-one-to-one/:id',
        component: SampleOneToOneDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleProjectApp.sampleOneToOne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sampleOneToOnePopupRoute: Routes = [
    {
        path: 'sample-one-to-one-new',
        component: SampleOneToOnePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleProjectApp.sampleOneToOne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sample-one-to-one/:id/edit',
        component: SampleOneToOnePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleProjectApp.sampleOneToOne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sample-one-to-one/:id/delete',
        component: SampleOneToOneDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleProjectApp.sampleOneToOne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
