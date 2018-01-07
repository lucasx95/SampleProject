/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { SampleProjectTestModule } from '../../../test.module';
import { SampleOneToOneComponent } from '../../../../../../main/webapp/app/entities/sample-one-to-one/sample-one-to-one.component';
import { SampleOneToOneService } from '../../../../../../main/webapp/app/entities/sample-one-to-one/sample-one-to-one.service';
import { SampleOneToOne } from '../../../../../../main/webapp/app/entities/sample-one-to-one/sample-one-to-one.model';

describe('Component Tests', () => {

    describe('SampleOneToOne Management Component', () => {
        let comp: SampleOneToOneComponent;
        let fixture: ComponentFixture<SampleOneToOneComponent>;
        let service: SampleOneToOneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SampleProjectTestModule],
                declarations: [SampleOneToOneComponent],
                providers: [
                    SampleOneToOneService
                ]
            })
            .overrideTemplate(SampleOneToOneComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SampleOneToOneComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SampleOneToOneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new SampleOneToOne(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.sampleOneToOnes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
