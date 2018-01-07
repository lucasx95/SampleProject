import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SampleProjectSharedModule } from '../../shared';
import {
    SampleManyService,
    SampleManyPopupService,
    SampleManyComponent,
    SampleManyDetailComponent,
    SampleManyDialogComponent,
    SampleManyPopupComponent,
    SampleManyDeletePopupComponent,
    SampleManyDeleteDialogComponent,
    sampleManyRoute,
    sampleManyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...sampleManyRoute,
    ...sampleManyPopupRoute,
];

@NgModule({
    imports: [
        SampleProjectSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SampleManyComponent,
        SampleManyDetailComponent,
        SampleManyDialogComponent,
        SampleManyDeleteDialogComponent,
        SampleManyPopupComponent,
        SampleManyDeletePopupComponent,
    ],
    entryComponents: [
        SampleManyComponent,
        SampleManyDialogComponent,
        SampleManyPopupComponent,
        SampleManyDeleteDialogComponent,
        SampleManyDeletePopupComponent,
    ],
    providers: [
        SampleManyService,
        SampleManyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SampleProjectSampleManyModule {}
