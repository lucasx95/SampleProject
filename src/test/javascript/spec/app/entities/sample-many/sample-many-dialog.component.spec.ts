/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SampleProjectTestModule } from '../../../test.module';
import { SampleManyDialogComponent } from '../../../../../../main/webapp/app/entities/sample-many/sample-many-dialog.component';
import { SampleManyService } from '../../../../../../main/webapp/app/entities/sample-many/sample-many.service';
import { SampleMany } from '../../../../../../main/webapp/app/entities/sample-many/sample-many.model';
import { SampleService } from '../../../../../../main/webapp/app/entities/sample';

describe('Component Tests', () => {

    describe('SampleMany Management Dialog Component', () => {
        let comp: SampleManyDialogComponent;
        let fixture: ComponentFixture<SampleManyDialogComponent>;
        let service: SampleManyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SampleProjectTestModule],
                declarations: [SampleManyDialogComponent],
                providers: [
                    SampleService,
                    SampleManyService
                ]
            })
            .overrideTemplate(SampleManyDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SampleManyDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SampleManyService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SampleMany(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.sampleMany = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sampleManyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SampleMany();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.sampleMany = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sampleManyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
