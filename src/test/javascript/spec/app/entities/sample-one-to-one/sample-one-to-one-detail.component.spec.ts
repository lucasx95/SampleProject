/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { SampleProjectTestModule } from '../../../test.module';
import { SampleOneToOneDetailComponent } from '../../../../../../main/webapp/app/entities/sample-one-to-one/sample-one-to-one-detail.component';
import { SampleOneToOneService } from '../../../../../../main/webapp/app/entities/sample-one-to-one/sample-one-to-one.service';
import { SampleOneToOne } from '../../../../../../main/webapp/app/entities/sample-one-to-one/sample-one-to-one.model';

describe('Component Tests', () => {

    describe('SampleOneToOne Management Detail Component', () => {
        let comp: SampleOneToOneDetailComponent;
        let fixture: ComponentFixture<SampleOneToOneDetailComponent>;
        let service: SampleOneToOneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SampleProjectTestModule],
                declarations: [SampleOneToOneDetailComponent],
                providers: [
                    SampleOneToOneService
                ]
            })
            .overrideTemplate(SampleOneToOneDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SampleOneToOneDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SampleOneToOneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new SampleOneToOne(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.sampleOneToOne).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
