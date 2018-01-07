import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SampleProjectSampleModule } from './sample/sample.module';
import { SampleProjectSampleOneToOneModule } from './sample-one-to-one/sample-one-to-one.module';
import { SampleProjectSampleManyModule } from './sample-many/sample-many.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SampleProjectSampleModule,
        SampleProjectSampleOneToOneModule,
        SampleProjectSampleManyModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SampleProjectEntityModule {}
