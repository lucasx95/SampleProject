/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { SampleProjectTestModule } from '../../../test.module';
import { SampleManyComponent } from '../../../../../../main/webapp/app/entities/sample-many/sample-many.component';
import { SampleManyService } from '../../../../../../main/webapp/app/entities/sample-many/sample-many.service';
import { SampleMany } from '../../../../../../main/webapp/app/entities/sample-many/sample-many.model';

describe('Component Tests', () => {

    describe('SampleMany Management Component', () => {
        let comp: SampleManyComponent;
        let fixture: ComponentFixture<SampleManyComponent>;
        let service: SampleManyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SampleProjectTestModule],
                declarations: [SampleManyComponent],
                providers: [
                    SampleManyService
                ]
            })
            .overrideTemplate(SampleManyComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SampleManyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SampleManyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new SampleMany(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.sampleManies[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
